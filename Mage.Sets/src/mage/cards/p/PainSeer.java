package mage.cards.p;

import mage.MageInt;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PainSeer extends CardImpl {

    public PainSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Inspired</i> &mdash; Whenever Pain Seer becomes untapped, reveal the top card of your library and put that card into your hand. You lose life equal to that card's converted mana cost.
        this.addAbility(new InspiredAbility(new RevealPutInHandLoseLifeEffect()));
    }

    private PainSeer(final PainSeer card) {
        super(card);
    }

    @Override
    public PainSeer copy() {
        return new PainSeer(this);
    }
}
