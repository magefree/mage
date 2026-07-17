package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeritusOfIdeation extends PrepareCard {

    public EmeritusOfIdeation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}", "Ancestral Recall", new CardType[]{CardType.INSTANT}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Whenever this creature attacks, you may exile eight cards from your graveyard. If you do, this creature becomes prepared.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BecomePreparedSourceEffect(), new ExileFromGraveCost(new TargetCardInYourGraveyard(8))
        )));

        // Ancestral Recall
        // Instant {U}
        // Target player draws three cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private EmeritusOfIdeation(final EmeritusOfIdeation card) {
        super(card);
    }

    @Override
    public EmeritusOfIdeation copy() {
        return new EmeritusOfIdeation(this);
    }
}
