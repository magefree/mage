package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillPledgemage extends CardImpl {

    public SilverquillPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Silverquill Pledgemage gains your choice of flying or lifelink until end of turn.
        this.addAbility(new MagecraftAbility(new SilverquillPledgemageEffect()));
    }

    private SilverquillPledgemage(final SilverquillPledgemage card) {
        super(card);
    }

    @Override
    public SilverquillPledgemage copy() {
        return new SilverquillPledgemage(this);
    }
}

class SilverquillPledgemageEffect extends OneShotEffect {

    SilverquillPledgemageEffect() {
        super(Outcome.Benefit);
        staticText = "{this} gains your choice of flying or lifelink until end of turn";
    }

    private SilverquillPledgemageEffect(final SilverquillPledgemageEffect effect) {
        super(effect);
    }

    @Override
    public SilverquillPledgemageEffect copy() {
        return new SilverquillPledgemageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        game.addEffect(new GainAbilitySourceEffect(player.chooseUse(
                Outcome.Neutral, "Choose flying or lifelink", null,
                "Flying", "Lifelink", source, game
        ) ? FlyingAbility.getInstance() : LifelinkAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}
