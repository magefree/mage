package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class HeroOfOxidRidge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 1 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 1));
    }

    public HeroOfOxidRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BattleCryAbility());
        this.addAbility(new AttacksTriggeredAbility(new CantBlockAllEffect(filter, Duration.EndOfTurn)));
    }

    private HeroOfOxidRidge(final HeroOfOxidRidge card) {
        super(card);
    }

    @Override
    public HeroOfOxidRidge copy() {
        return new HeroOfOxidRidge(this);
    }

}
