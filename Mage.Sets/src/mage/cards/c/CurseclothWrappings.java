package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Jmlundeen
 */
public final class CurseclothWrappings extends CardImpl {
    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombies you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }
    public CurseclothWrappings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");
        

        // Zombies you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)));
        // {T}: Target creature card in your graveyard gains embalm until end of turn. The embalm cost is equal to its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new CurseClothWrappingsEffect(),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard")));
        this.addAbility(ability);
    }

    private CurseclothWrappings(final CurseclothWrappings card) {
        super(card);
    }

    @Override
    public CurseclothWrappings copy() {
        return new CurseclothWrappings(this);
    }
}

class CurseClothWrappingsEffect extends ContinuousEffectImpl {
    public CurseClothWrappingsEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Target creature card in your graveyard gains embalm until end of turn. " +
                "The embalm cost is equal to its mana cost. (Exile that card and pay its embalm cost: " +
                "Create a token that's a copy of it, except it's a white Zombie in addition to its other types " +
                "and has no mana cost. Embalm only as a sorcery.)";
    }

    public CurseClothWrappingsEffect(final CurseClothWrappingsEffect effect) {
        super(effect);
    }

    @Override
    public CurseClothWrappingsEffect copy() {
        return new CurseClothWrappingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            EmbalmAbility embalmAbility = new EmbalmAbility(card.getManaCost(), card);
            game.getState().addOtherAbility(card, embalmAbility);
            return true;
        }
        return false;
    }

}
