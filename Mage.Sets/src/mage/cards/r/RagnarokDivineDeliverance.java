package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagnarokDivineDeliverance extends MeldCard {

    private static final FilterCard filter = new FilterPermanentCard("nonlegendary permanent card from your graveyard");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public RagnarokDivineDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
        this.color.setBlack(true);
        this.color.setGreen(true);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Ragnarok dies, destroy target permanent and return target nonlegendary permanent card from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("and return target nonlegendary permanent card from your graveyard to the battlefield")
                .setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetPermanent());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private RagnarokDivineDeliverance(final RagnarokDivineDeliverance card) {
        super(card);
    }

    @Override
    public RagnarokDivineDeliverance copy() {
        return new RagnarokDivineDeliverance(this);
    }
}
