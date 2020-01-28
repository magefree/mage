package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Derpthemeus
 */
public final class VulshokBattlemaster extends CardImpl {

    public VulshokBattlemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Vulshok Battlemaster enters the battlefield, attach all Equipment on the battlefield to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VulshokBattlemasterEffect()));
    }

    public VulshokBattlemaster(final VulshokBattlemaster card) {
        super(card);
    }

    @Override
    public VulshokBattlemaster copy() {
        return new VulshokBattlemaster(this);
    }

    static class VulshokBattlemasterEffect extends OneShotEffect {

        public VulshokBattlemasterEffect() {
            super(Outcome.Benefit);
            this.staticText = "attach all Equipment on the battlefield to it";
        }

        public VulshokBattlemasterEffect(final VulshokBattlemasterEffect effect) {
            super(effect);
        }

        @Override
        public VulshokBattlemasterEffect copy() {
            return new VulshokBattlemasterEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent battlemaster = game.getPermanent(source.getSourceId());
            if (battlemaster != null) {
                FilterPermanent filter = new FilterPermanent();
                filter.add(SubType.EQUIPMENT.getPredicate());
                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (equipment != null) {
                        //If an Equipment can't equip Vulshok Battlemaster, it isn't attached to the Battlemaster, and it doesn't become unattached (if it's attached to a creature). (https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=48125)
                        if (!battlemaster.cantBeAttachedBy(equipment, game, false)) {
                            battlemaster.addAttachment(equipment.getId(), game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
