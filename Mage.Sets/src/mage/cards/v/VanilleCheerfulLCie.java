package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanilleCheerfulLCie extends CardImpl {

    private static final Condition condition = new MeldCondition("Fang, Fearless l'Cie");
    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public VanilleCheerfulLCie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.meldsWithClazz = mage.cards.f.FangFearlessLCie.class;
        this.meldsToClazz = mage.cards.r.RagnarokDivineDeliverance.class;

        // When Vanille enters, mill two cards, then return a permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false, filter, PutCards.HAND)
                .concatBy(", then"));
        this.addAbility(ability);

        // At the beginning of your first main phase, if you both own and control Vanille and a creature named Fang, Fearless l'Cie, you may pay {3}{B}{G}. If you do, exile them, then meld them into Ragnarok, Divine Deliverance.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(
                new MeldEffect("Fang, Fearless l'Cie", "Ragnarok, Divine Deliverance")
                        .setText("exile them, then meld them into Ragnarok, Divine Deliverance"),
                new ManaCostsImpl<>("{3}{B}{G}")
        )).withInterveningIf(condition));
    }

    private VanilleCheerfulLCie(final VanilleCheerfulLCie card) {
        super(card);
    }

    @Override
    public VanilleCheerfulLCie copy() {
        return new VanilleCheerfulLCie(this);
    }
}
