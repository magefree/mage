package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;

/**
 * @author Loki
 */
public final class GeistOfSaintTraft extends CardImpl {

    public GeistOfSaintTraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever Geist of Saint Traft attacks, create a 4/4 white Angel creature token with flying tapped and attacking. Exile that token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new GeistOfSaintTraftEffect(), false));
    }

    private GeistOfSaintTraft(final GeistOfSaintTraft card) {
        super(card);
    }

    @Override
    public GeistOfSaintTraft copy() {
        return new GeistOfSaintTraft(this);
    }
}

class GeistOfSaintTraftEffect extends OneShotEffect {

    GeistOfSaintTraftEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 4/4 white Angel creature token with flying that's tapped and attacking. Exile that token at end of combat";
    }

    GeistOfSaintTraftEffect(final GeistOfSaintTraftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        CreateTokenEffect effect = new CreateTokenEffect(new AngelToken(), 1, true, true);
        Player controller = game.getPlayer(source.getControllerId());
        
        if (controller != null && effect.apply(game, source)) {
            effect.exileTokensCreatedAtEndOfCombat(game, source);
            return true;
        }
        return false;
    }

    @Override
    public GeistOfSaintTraftEffect copy() {
        return new GeistOfSaintTraftEffect(this);
    }
}
