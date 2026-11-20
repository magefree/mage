package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class KirkCommandingOfficer extends CardImpl {

    public KirkCommandingOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack while creatures you control have total power 8 or greater, exile the top card of your library. You may play that card this turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn), 1
        ).withTriggerCondition(FormidableCondition.instance));

        // {2}, Sacrifice Kirk: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private KirkCommandingOfficer(final KirkCommandingOfficer card) {
        super(card);
    }

    @Override
    public KirkCommandingOfficer copy() {
        return new KirkCommandingOfficer(this);
    }
}
