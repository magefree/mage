package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpawnbedProtector extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Eldrazi creature card from your graveyard");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }

    public SpawnbedProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // At the beginning of your end step, return up to one target Eldrazi creature card from your graveyard to your hand. Create two 1/1 colorless Eldrazi Scion creature tokens with "Sacrifice this creature: Add {C}."
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, false
        );
        ability.addEffect(new CreateTokenEffect(new EldraziScionToken(), 2));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private SpawnbedProtector(final SpawnbedProtector card) {
        super(card);
    }

    @Override
    public SpawnbedProtector copy() {
        return new SpawnbedProtector(this);
    }
}
