package mage.cards.j;

import mage.Mana;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.JayaBallardEmblem;
import mage.watchers.common.CastFromGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JayaBallard extends CardImpl {

    public JayaBallard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);
        this.setStartingLoyalty(5);

        // +1: Add {R}{R}{R}. Spend this mana only to cast instant or sorcery spells.
        this.addAbility(new LoyaltyAbility(new AddConditionalManaEffect(
                Mana.RedMana(3), new InstantOrSorcerySpellManaBuilder()
        ), 1));

        // +1: Discard up to three cards, then draw that many cards.
        this.addAbility(new LoyaltyAbility(new DiscardAndDrawThatManyEffect(3), 1));

        // −8: You get an emblem with "You may cast instant and sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new JayaBallardEmblem()), -8
        ), new CastFromGraveyardWatcher());
    }

    private JayaBallard(final JayaBallard card) {
        super(card);
    }

    @Override
    public JayaBallard copy() {
        return new JayaBallard(this);
    }
}
