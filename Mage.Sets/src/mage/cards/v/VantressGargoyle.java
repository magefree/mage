package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VantressGargoyle extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 4);

    public VantressGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vantress Gargoyle can't attack unless defending player has seven or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(new VantressGargoyleEffect()));

        // Vantress Gargoyle can't block unless you have four or more cards in hand.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield), condition,
                "{this} can't block unless you have four or more cards in hand"
        )));

        // {T}: Each player puts the top card of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new PutTopCardOfLibraryIntoGraveEachPlayerEffect(1, TargetController.ANY), new TapSourceCost()
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

class VantressGargoyleEffect extends RestrictionEffect {

    VantressGargoyleEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless defending player has seven or more cards in their graveyard";
    }

    private VantressGargoyleEffect(final VantressGargoyleEffect effect) {
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
    public VantressGargoyleEffect copy() {
        return new VantressGargoyleEffect(this);
    }
}