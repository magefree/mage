
package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SoulOfRavnica extends CardImpl {

    public SoulOfRavnica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {5}{U}{U}: Draw a card for each color among permanents you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulOfRavnicaEffect(), new ManaCostsImpl("{5}{U}{U}")));

        // {5}{U}{U}, Exile Soul of Ravnica from your graveyard: Draw a card for each color among permanents you control.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new SoulOfRavnicaEffect(), new ManaCostsImpl("{5}{U}{U}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    public SoulOfRavnica(final SoulOfRavnica card) {
        super(card);
    }

    @Override
    public SoulOfRavnica copy() {
        return new SoulOfRavnica(this);
    }
}

class SoulOfRavnicaEffect extends OneShotEffect {

    public SoulOfRavnicaEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each color among permanents you control";
    }

    public SoulOfRavnicaEffect(final SoulOfRavnicaEffect effect) {
        super(effect);
    }

    @Override
    public SoulOfRavnicaEffect copy() {
        return new SoulOfRavnicaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<ObjectColor> colors = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (permanent.getColor(game).isBlack()) {
                    colors.add(ObjectColor.BLACK);
                }
                if (permanent.getColor(game).isBlue()) {
                    colors.add(ObjectColor.BLUE);
                }
                if (permanent.getColor(game).isRed()) {
                    colors.add(ObjectColor.RED);
                }
                if (permanent.getColor(game).isGreen()) {
                    colors.add(ObjectColor.GREEN);
                }
                if (permanent.getColor(game).isWhite()) {
                    colors.add(ObjectColor.WHITE);
                }
            }
            controller.drawCards(colors.size(), game);
            return true;
        }
        return false;
    }
}
