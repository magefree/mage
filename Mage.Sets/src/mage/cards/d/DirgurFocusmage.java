package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DirgurFocusmage extends PrepareCard {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");
    private static final FilterSpell filter2 = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 5 or greater from your hand");

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
        filter2.add(new CastFromZonePredicate(Zone.HAND));
        filter2.add(TargetController.YOU.getOwnerPredicate());
    }

    public DirgurFocusmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}", "Braingeyser", new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell with mana value 5 or greater from your hand, this creature becomes prepared.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BecomePreparedSourceEffect(), filter2, false));

        // Braingeyser
        // Sorcery {X}{U}{U}
        // Target player draws X cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(GetXValue.instance));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private DirgurFocusmage(final DirgurFocusmage card) {
        super(card);
    }

    @Override
    public DirgurFocusmage copy() {
        return new DirgurFocusmage(this);
    }
}
