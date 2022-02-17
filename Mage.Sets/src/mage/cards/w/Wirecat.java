package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Derpthemeus
 */
public final class Wirecat extends CardImpl {

    public Wirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Wirecat can't attack or block if an enchantment is on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WirecatEffect()));
    }

    private Wirecat(final Wirecat card) {
        super(card);
    }

    @Override
    public Wirecat copy() {
        return new Wirecat(this);
    }

    static class WirecatEffect extends RestrictionEffect {

        public WirecatEffect() {
            super(Duration.WhileOnBattlefield);
            staticText = "{this} can't attack or block if an enchantment is on the battlefield";
        }

        public WirecatEffect(final WirecatEffect effect) {
            super(effect);
        }

        @Override
        public WirecatEffect copy() {
            return new WirecatEffect(this);
        }

        @Override
        public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
            return false;
        }

        @Override
        public boolean canBlockCheckAfter(Ability source, Game game, boolean canUseChooseDialogs) {
            return false;
        }

        @Override
        public boolean applies(Permanent permanent, Ability source, Game game) {
            if (permanent.getId().equals(source.getSourceId())) {
                return game.getBattlefield().contains(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, source, game, 1);
            }
            return false;
        }
    }
}
