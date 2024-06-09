package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.DrawCardForEachColorAmongControlledPermanentsEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.ManaPoolItem;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ChromaticOrrery extends CardImpl {

    public ChromaticOrrery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // You may spend mana as though it were mana of any color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChromaticOrreryEffect()));

        // {T}: Add {C}{C}{C}{C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(5), new TapSourceCost()));

        // {5}, {T}: Draw a card for each color among permanents you control.
        Ability ability = new SimpleActivatedAbility(new DrawCardForEachColorAmongControlledPermanentsEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ChromaticOrrery(final ChromaticOrrery card) {
        super(card);
    }

    @Override
    public ChromaticOrrery copy() {
        return new ChromaticOrrery(this);
    }
}

class ChromaticOrreryEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    ChromaticOrreryEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any color";
    }

    private ChromaticOrreryEffect(ChromaticOrreryEffect effect) {
        super(effect);
    }

    @Override
    public ChromaticOrreryEffect copy() {
        return new ChromaticOrreryEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}