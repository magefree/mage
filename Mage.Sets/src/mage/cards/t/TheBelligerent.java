package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBelligerent extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast spells");

    public TheBelligerent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever The Belligerent attacks, create a Treasure token. Until end of turn, you may look at the top card of your library any time, and you may play lands and cast spells from the top of your library.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken()));
        ability.addEffect(new LookAtTopCardOfLibraryAnyTimeEffect(TargetController.YOU, Duration.EndOfTurn));
        ability.addEffect(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        ).concatBy(", and"));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private TheBelligerent(final TheBelligerent card) {
        super(card);
    }

    @Override
    public TheBelligerent copy() {
        return new TheBelligerent(this);
    }
}
