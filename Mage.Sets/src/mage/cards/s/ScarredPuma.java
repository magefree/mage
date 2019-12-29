package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Derpthemeus
 */
public final class ScarredPuma extends CardImpl {

    public ScarredPuma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Scarred Puma can't attack unless a black or green creature also attacks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ScarredPumaEffect()));
    }

    public ScarredPuma(final ScarredPuma card) {
        super(card);
    }

    @Override
    public ScarredPuma copy() {
        return new ScarredPuma(this);
    }

    static class ScarredPumaEffect extends RestrictionEffect {

        private final FilterAttackingCreature filter = new FilterAttackingCreature();

        public ScarredPumaEffect() {
            super(Duration.WhileOnBattlefield);
            staticText = "{this} can't attack unless a black or green creature also attacks";
        }

        public ScarredPumaEffect(final ScarredPumaEffect effect) {
            super(effect);
        }

        @Override
        public ScarredPumaEffect copy() {
            return new ScarredPumaEffect(this);
        }

        @Override
        public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
            return false;
        }

        @Override
        public boolean applies(Permanent permanent, Ability source, Game game) {
            if (permanent.getId().equals(source.getSourceId())) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    //excludes itself (https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=23067)
                    if (!Objects.equals(creature.getId(), source.getSourceId())) {
                        ObjectColor color = creature.getColor(game);
                        if (color.isBlack() || color.isGreen()) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
