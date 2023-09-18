package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolemnDoomguide extends CardImpl {

    public SolemnDoomguide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature card in your graveyard that's a Cleric, Rogue, Warrior, and/or Wizard has unearth {1}{B}
        this.addAbility(new SimpleStaticAbility(new SolemnDoomguideEffect()));
    }

    private SolemnDoomguide(final SolemnDoomguide card) {
        super(card);
    }

    @Override
    public SolemnDoomguide copy() {
        return new SolemnDoomguide(this);
    }
}

class SolemnDoomguideEffect extends ContinuousEffectImpl {

    SolemnDoomguideEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each creature card in your graveyard that's " +
                "a Cleric, Rogue, Warrior, and/or Wizard has unearth {1}{B}";
    }

    private SolemnDoomguideEffect(final SolemnDoomguideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card == null || !card.isCreature(game)
                    || !(card.hasSubtype(SubType.CLERIC, game)
                    || card.hasSubtype(SubType.ROGUE, game)
                    || card.hasSubtype(SubType.WARRIOR, game)
                    || card.hasSubtype(SubType.WIZARD, game))) {
                continue;
            }
            UnearthAbility ability = new UnearthAbility(new ManaCostsImpl<>("{1}{B}"));
            ability.setSourceId(cardId);
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public SolemnDoomguideEffect copy() {
        return new SolemnDoomguideEffect(this);
    }
}