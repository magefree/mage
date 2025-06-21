package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WebstrikeElite extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with mana value X");

    public WebstrikeElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Cycling {X}{G}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{G}{G}")));

        // When you cycle this card, destroy up to one target artifact or enchantment with mana value X.
        Ability ability = new CycleTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(new ManaValueTargetAdjuster(new EffectKeyValue("cycleXValue"), ComparisonType.EQUAL_TO));
        this.addAbility(ability);
    }

    private WebstrikeElite(final WebstrikeElite card) {
        super(card);
    }

    @Override
    public WebstrikeElite copy() {
        return new WebstrikeElite(this);
    }
}
