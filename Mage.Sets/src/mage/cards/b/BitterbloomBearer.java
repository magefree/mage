package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FaerieBlueBlackToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BitterbloomBearer extends CardImpl {

    public BitterbloomBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you lose 1 life and create a 1/1 blue and black Faerie creature token with flying.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new FaerieBlueBlackToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private BitterbloomBearer(final BitterbloomBearer card) {
        super(card);
    }

    @Override
    public BitterbloomBearer copy() {
        return new BitterbloomBearer(this);
    }
}
