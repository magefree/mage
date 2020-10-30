package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VantressGargoyle extends CardImpl {

    public VantressGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vantress Gargoyle can't attack unless defending player has seven or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(new VantressGargoyleAttackEffect()));

        // Vantress Gargoyle can't block unless you have four or more cards in hand.
        this.addAbility(new SimpleStaticAbility(new VantressGargoyleBlockEffect()));

        // {T}: Each player puts the top card of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsEachPlayerEffect(1, TargetController.ANY), new TapSourceCost()
        ));
    }

    private VantressGargoyle(final VantressGargoyle card) {
        super(card);
    }

    @Override
    public VantressGargoyle copy() {
        return new VantressGargoyle(this);
    }
}

class VantressGargoyleAttackEffect extends RestrictionEffect {

    VantressGargoyleAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless defending player has seven or more cards in their graveyard";
    }

    private VantressGargoyleAttackEffect(final VantressGargoyleAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        Player player = game.getPlayerOrPlaneswalkerController(defenderId);
        return player != null && player.getGraveyard().size() > 6;
    }

    @Override
    public VantressGargoyleAttackEffect copy() {
        return new VantressGargoyleAttackEffect(this);
    }
}

class VantressGargoyleBlockEffect extends RestrictionEffect {

    VantressGargoyleBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless you have four or more cards in hand";
    }

    private VantressGargoyleBlockEffect(final VantressGargoyleBlockEffect effect) {
        super(effect);
    }

    @Override
    public VantressGargoyleBlockEffect copy() {
        return new VantressGargoyleBlockEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null
                && player.getHand().size() < 4
                && permanent.getId().equals(source.getSourceId());
    }
}
