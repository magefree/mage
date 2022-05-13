package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.OpponentSacrificesNonTokenPermanentTriggeredAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ItThatBetrays extends CardImpl {

    private static final FilterPermanent FILTER = new FilterPermanent("nontoken permanent");

    static {
        FILTER.add(TokenPredicate.FALSE);
    }

    public ItThatBetrays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{12}");
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // Annihilator 2 (Whenever this creature attacks, defending player sacrifices two permanents.)
        this.addAbility(new AnnihilatorAbility(2));

        // Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.
        this.addAbility(new OpponentSacrificesNonTokenPermanentTriggeredAbility(
                new ReturnToBattlefieldUnderYourControlTargetEffect()
                        .setText("put that card onto the battlefield under your control")
        ));
    }

    private ItThatBetrays(final ItThatBetrays card) {
        super(card);
    }

    @Override
    public ItThatBetrays copy() {
        return new ItThatBetrays(this);
    }
}
