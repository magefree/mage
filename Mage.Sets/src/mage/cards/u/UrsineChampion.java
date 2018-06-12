package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class UrsineChampion extends CardImpl {

    public UrsineChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {5}{G}: Ursine Champion gets +3/+3 and becomes a Bear Berserker until end of turn. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn)
                        .setText("{this} gets +3/+3"),
                new ManaCostsImpl("{5}{G}")
        );
        ability.addEffect(new UrsineChampionEffect());
        this.addAbility(ability);
    }

    public UrsineChampion(final UrsineChampion card) {
        super(card);
    }

    @Override
    public UrsineChampion copy() {
        return new UrsineChampion(this);
    }

    private class UrsineChampionEffect extends ContinuousEffectImpl {

        public UrsineChampionEffect() {
            super(Duration.EndOfTurn, Outcome.BecomeCreature);
            setText();
        }

        public UrsineChampionEffect(final UrsineChampionEffect effect) {
            super(effect);
        }

        @Override
        public UrsineChampionEffect copy() {
            return new UrsineChampionEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getSubtype(game).clear();
                        permanent.getSubtype(game).add(SubType.BEAR);
                        permanent.getSubtype(game).add(SubType.BERSERKER);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        private void setText() {
            staticText = "and becomes a Bear Berserker until end of turn";
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
        }
    }
}
