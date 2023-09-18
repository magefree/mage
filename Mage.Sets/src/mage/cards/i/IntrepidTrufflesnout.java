package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class IntrepidTrufflesnout extends AdventureCard {

    public IntrepidTrufflesnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{G}", "Go Hog Wild", "{1}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Intrepid Trufflesnout attacks alone, create a Food token.
        this.addAbility(new AttacksAloneSourceTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Target creature gets +2/+2 until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private IntrepidTrufflesnout(final IntrepidTrufflesnout card) {
        super(card);
    }

    @Override
    public IntrepidTrufflesnout copy() {
        return new IntrepidTrufflesnout(this);
    }
}
