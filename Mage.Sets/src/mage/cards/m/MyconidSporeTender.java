package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyconidSporeTender extends CardImpl {

    public MyconidSporeTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Infesting Spores — When Myconid Spore Tender enters the battlefield, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability.withFlavorWord("Infesting Spores"));
    }

    private MyconidSporeTender(final MyconidSporeTender card) {
        super(card);
    }

    @Override
    public MyconidSporeTender copy() {
        return new MyconidSporeTender(this);
    }
}
