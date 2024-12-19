package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PolJamaarIllusionist extends CardImpl {

    public PolJamaarIllusionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Pol Jamaar, Illusionist enters, choose a creature type. Draw a card for each creature you control of that type.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PolJamaarIllusionistEffect()));
    }

    private PolJamaarIllusionist(final PolJamaarIllusionist card) {
        super(card);
    }

    @Override
    public PolJamaarIllusionist copy() {
        return new PolJamaarIllusionist(this);
    }
}

class PolJamaarIllusionistEffect extends OneShotEffect {

    PolJamaarIllusionistEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Draw a card for each creature you control of that type";
    }

    private PolJamaarIllusionistEffect(final PolJamaarIllusionistEffect effect) {
        super(effect);
    }

    @Override
    public PolJamaarIllusionistEffect copy() {
        return new PolJamaarIllusionistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceCreatureType choice = new ChoiceCreatureType(game, source);
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        SubType subType = SubType.byDescription(choice.getChoiceKey());
        game.informPlayers(player.getLogName() + " chooses " + subType);
        int amount = game
                .getBattlefield()
                .count(
                        new FilterControlledCreaturePermanent(subType),
                        source.getControllerId(), source, game
                );
        return amount > 0 && player.drawCards(amount, source, game) > 0;
    }
}
