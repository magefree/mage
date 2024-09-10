package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessCottage extends CardImpl {

    public RestlessCottage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Cottage enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {2}{B}{G}: Restless Cottage becomes a 4/4 black and green Horror creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 black and green Horror creature")
                        .withColor("BG").withSubType(SubType.HORROR),
                CardType.LAND, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{B}{G}")));

        // Whenever Restless Cottage attacks, create a Food token and exile up to one target card from a graveyard.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new FoodToken()));
        ability.addEffect(new ExileTargetEffect().concatBy("and"));
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);
    }

    private RestlessCottage(final RestlessCottage card) {
        super(card);
    }

    @Override
    public RestlessCottage copy() {
        return new RestlessCottage(this);
    }
}
