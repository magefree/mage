package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.GarrukCursedHuntsmanEmblem;
import mage.game.permanent.token.GarrukCursedHuntsmanToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarrukCursedHuntsman extends CardImpl {

    public GarrukCursedHuntsman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.setStartingLoyalty(5);

        // 0: Create two 2/2 black and green Wolf creature tokens with "When this creature dies, put a loyalty counter on each Garruk you control."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new GarrukCursedHuntsmanToken(), 2), 0));

        // −3: Destroy target creature. Draw a card.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("Draw a card"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −6: You get an emblem with "Creatures you control get +3/+3 and have trample."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GarrukCursedHuntsmanEmblem()), -6));
    }

    private GarrukCursedHuntsman(final GarrukCursedHuntsman card) {
        super(card);
    }

    @Override
    public GarrukCursedHuntsman copy() {
        return new GarrukCursedHuntsman(this);
    }
}
