package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ShiftingWoodland extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    private static final FilterCard filterCard = new FilterPermanentCard("permanent card in your graveyard");

    public ShiftingWoodland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Shifting Woodland enters the battlefield tapped unless you control a Forest.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Delirium -- {2}{G}{G}: Shifting Woodland becomes a copy of target permanent card in your graveyard until end of turn. Activate only if there are four or more card types among cards in your graveyard.
        Ability ability = new ConditionalActivatedAbility(
                new ShiftingWoodlandCopyEffect(),
                new ManaCostsImpl<>("{2}{G}{G}"),
                DeliriumCondition.instance
        );
        ability.addTarget(new TargetCardInYourGraveyard(1, filterCard));
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        this.addAbility(ability.addHint(CardTypesInGraveyardHint.YOU));
    }

    private ShiftingWoodland(final ShiftingWoodland card) {
        super(card);
    }

    @Override
    public ShiftingWoodland copy() {
        return new ShiftingWoodland(this);
    }
}

class ShiftingWoodlandCopyEffect extends OneShotEffect {

    ShiftingWoodlandCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of target permanent card in your graveyard until end of turn";
    }

    private ShiftingWoodlandCopyEffect(final ShiftingWoodlandCopyEffect effect) {
        super(effect);
    }

    @Override
    public ShiftingWoodlandCopyEffect copy() {
        return new ShiftingWoodlandCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        Card copyFromCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (copyFromCard == null) {
            return false;
        }
        Permanent blueprint = new PermanentCard(copyFromCard, source.getControllerId(), game);
        game.copyPermanent(Duration.EndOfTurn, blueprint, sourcePermanent.getId(), source, new EmptyCopyApplier());
        return true;
    }
}