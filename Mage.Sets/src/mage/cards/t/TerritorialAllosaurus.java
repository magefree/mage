package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TerritorialAllosaurus extends CardImpl {

    public TerritorialAllosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // When Territorial Allosaurus enters the battlefield, if it was kicked, it fights another target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect())
                .withInterveningIf(KickedCondition.ONCE)
                .withRuleTextReplacement(true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private TerritorialAllosaurus(final TerritorialAllosaurus card) {
        super(card);
    }

    @Override
    public TerritorialAllosaurus copy() {
        return new TerritorialAllosaurus(this);
    }
}
