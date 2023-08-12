package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VizierOfTheMenagerie extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("cast creature spells");

    public VizierOfTheMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library. (You may do this at any time.)
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        )));

        // You may spend mana as though it were mana of any type to cast creature spells.
        this.addAbility(new SimpleStaticAbility(new VizierOfTheMenagerieManaEffect()));
    }

    private VizierOfTheMenagerie(final VizierOfTheMenagerie card) {
        super(card);
    }

    @Override
    public VizierOfTheMenagerie copy() {
        return new VizierOfTheMenagerie(this);
    }
}

class VizierOfTheMenagerieManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public VizierOfTheMenagerieManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any type to cast creature spells";
    }

    public VizierOfTheMenagerieManaEffect(final VizierOfTheMenagerieManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VizierOfTheMenagerieManaEffect copy() {
        return new VizierOfTheMenagerieManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (source.isControlledBy(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            return mageObject != null && mageObject.isCreature(game);
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
