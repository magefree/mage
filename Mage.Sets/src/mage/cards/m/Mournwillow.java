package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Mournwillow extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");
    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    public Mournwillow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // <i>Delirium</i> &mdash; When Mournwillow enters the battlefield, if there are four or more card types among cards in your graveyard,
        // creatures with power 2 or less can't block this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CantBlockAllEffect(filter, Duration.EndOfTurn))
                .withInterveningIf(DeliriumCondition.instance)
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private Mournwillow(final Mournwillow card) {
        super(card);
    }

    @Override
    public Mournwillow copy() {
        return new Mournwillow(this);
    }
}
