package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningNightbonder extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterSpell filter2 = new FilterSpell();
    private static final Predicate predicate = new AbilityPredicate(FlashAbility.class);

    static {
        filter.add(predicate);
        filter2.add(predicate);
    }

    public CunningNightbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Spells you cast with flash cost {1} less to cast and can't be countered.
        Ability ability = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("spells with flash you cast cost {1} less to cast"));
        ability.addEffect(new CantBeCounteredControlledEffect(filter2, Duration.WhileOnBattlefield)
                .setText("and can't be countered"));
        this.addAbility(ability);
    }

    private CunningNightbonder(final CunningNightbonder card) {
        super(card);
    }

    @Override
    public CunningNightbonder copy() {
        return new CunningNightbonder(this);
    }
}
