package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class QueenMotherRamonda extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    public QueenMotherRamonda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Queen Mother Ramonda enters, you become the monarch.
        this.addAbility(
            new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect())
                .addHint(MonarchHint.instance)
        );

        // As long as you're the monarch, creatures with power 2 or less can't attack you.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
            new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filter),
            MonarchIsSourceControllerCondition.instance,
            "as long as you're the monarch, creatures with power 2 or less can't attack you"
        )).addHint(MonarchHint.instance));
    }

    private QueenMotherRamonda(final QueenMotherRamonda card) {
        super(card);
    }

    @Override
    public QueenMotherRamonda copy() {
        return new QueenMotherRamonda(this);
    }
}
