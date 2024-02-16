
package mage.cards.w;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class WorldheartPhoenix extends CardImpl {

    public WorldheartPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new WorldheartPhoenixPlayEffect())
                .setIdentifier(MageIdentifier.WorldheartPhoenixAlternateCast);
        ability.addEffect(new EntersBattlefieldEffect(new WorldheartPhoenixEntersBattlefieldEffect(),
                "If you do, it enters the battlefield with two +1/+1 counters on it"));
        this.addAbility(ability);

    }

    private WorldheartPhoenix(final WorldheartPhoenix card) {
        super(card);
    }

    @Override
    public WorldheartPhoenix copy() {
        return new WorldheartPhoenix(this);
    }

    class WorldheartPhoenixPlayEffect extends AsThoughEffectImpl {

        public WorldheartPhoenixPlayEffect() {
            super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
            staticText = "You may cast {this} from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost";
        }

        private WorldheartPhoenixPlayEffect(final WorldheartPhoenixPlayEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public WorldheartPhoenixPlayEffect copy() {
            return new WorldheartPhoenixPlayEffect(this);
        }

        @Override
        public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
            if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
                if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                    Player player = game.getPlayer(affectedControllerId);
                    if (player != null) {
                        player.setCastSourceIdWithAlternateMana(
                                sourceId, new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), null,
                                MageIdentifier.WorldheartPhoenixAlternateCast
                        );
                        return true;
                    }
                }
            }
            return false;
        }

    }

    class WorldheartPhoenixEntersBattlefieldEffect extends OneShotEffect {

        public WorldheartPhoenixEntersBattlefieldEffect() {
            super(Outcome.BoostCreature);
            staticText = "If you do, it enters the battlefield with two +1/+1 counters on it";
        }

        private WorldheartPhoenixEntersBattlefieldEffect(final WorldheartPhoenixEntersBattlefieldEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanentEntering(source.getSourceId());
            if (permanent != null) {
                SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
                if (spellAbility != null
                        && spellAbility.getSourceId().equals(source.getSourceId())
                        && permanent.getZoneChangeCounter(game) == spellAbility.getSourceObjectZoneChangeCounter()) {
                    // TODO: No perfect solution because there could be other effects that allow to cast the card for this mana cost
                    if (spellAbility.getManaCosts().getText().equals("{W}{U}{B}{R}{G}")) {
                        permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                    }
                }
            }
            return true;
        }

        @Override
        public WorldheartPhoenixEntersBattlefieldEffect copy() {
            return new WorldheartPhoenixEntersBattlefieldEffect(this);
        }

    }

}
