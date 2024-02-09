package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author DominionSpy
 */
public final class ReliveThePast extends CardImpl {

    private static final FilterEnchantmentCard filter = new FilterEnchantmentCard("non-Aura enchantment");

    static {
        filter.add(Predicates.not(SubType.AURA.getPredicate()));
    }

    public ReliveThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{W}");

        // Return up to one target artifact card, up to one target land card, and up to one target non-Aura enchantment card from your graveyard to the battlefield.
        getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("Return up to one target artifact card, up to one target land card, and up to one target non-Aura enchantment card from your graveyard to the battlefield."));
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT));
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_LAND));
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));

        // They are 5/5 Elemental creatures in addition to their other types.
        getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
                new ReliveThePastToken(), false, false, Duration.WhileOnBattlefield)
                .setTargetPointer(new EachTargetPointer())
                .setText("They are 5/5 Elemental creatures in addition to their other types."));
    }

    private ReliveThePast(final ReliveThePast card) {
        super(card);
    }

    @Override
    public ReliveThePast copy() {
        return new ReliveThePast(this);
    }
}

class ReliveThePastToken extends TokenImpl {

    ReliveThePastToken() {
        super("", "5/5 Elemental creatures");
        power = new MageInt(5);
        toughness = new MageInt(5);
        subtype.add(SubType.ELEMENTAL);
        cardType.add(CardType.CREATURE);
    }

    private ReliveThePastToken(final ReliveThePastToken token) {
        super(token);
    }

    @Override
    public ReliveThePastToken copy() {
        return new ReliveThePastToken(this);
    }
}
