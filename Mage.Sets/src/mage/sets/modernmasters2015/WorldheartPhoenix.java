/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.modernmasters2015;

import java.util.UUID;
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
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WorldheartPhoenix extends CardImpl {

    public WorldheartPhoenix(UUID ownerId) {
        super(ownerId, 135, "Worldheart Phoenix", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "MM2";
        this.subtype.add("Phoenix");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new WorldheartPhoenixPlayEffect());
        ability.addEffect(new EntersBattlefieldEffect(new WorldheartPhoenixEntersBattlefieldEffect(),
                "If you do, it enters the battlefield with two +1/+1 counters on it"));
        this.addAbility(ability);

    }

    public WorldheartPhoenix(final WorldheartPhoenix card) {
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

        public WorldheartPhoenixPlayEffect(final WorldheartPhoenixPlayEffect effect) {
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
            if (sourceId.equals(source.getSourceId()) && source.getControllerId().equals(affectedControllerId)) {
                if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                    Player player = game.getPlayer(affectedControllerId);
                    if (player != null) {
                        // can sometimes be cast with base mana cost from grave????
                        player.setCastSourceIdWithAlternateMana(sourceId, new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
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

        public WorldheartPhoenixEntersBattlefieldEffect(final WorldheartPhoenixEntersBattlefieldEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
                if (obj != null && obj instanceof SpellAbility
                        && permanent.getZoneChangeCounter(game) - 1 == ((SpellAbility) obj).getSourceObjectZoneChangeCounter()) {
                    // TODO: No perfect solution because there could be other effects that allow to cast the card for this mana cost
                    if (((SpellAbility) obj).getManaCosts().getText().equals("{W}{U}{B}{R}{G}")) {
                        permanent.addCounters(CounterType.P1P1.createInstance(2), game);
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
