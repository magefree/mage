package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class XuIfitOsteoharmonist extends CardImpl {

    public XuIfitOsteoharmonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Return target creature card from your graveyard to the battlefield. It's a Skeleton in addition to its other types and has no abilities. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new TapSourceCost()
        );
        ability.addEffect(new XuIfitOsteoharmonistEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private XuIfitOsteoharmonist(final XuIfitOsteoharmonist card) {
        super(card);
    }

    @Override
    public XuIfitOsteoharmonist copy() {
        return new XuIfitOsteoharmonist(this);
    }
}

class XuIfitOsteoharmonistEffect extends ContinuousEffectImpl {

    XuIfitOsteoharmonistEffect() {
        super(Duration.Custom, Outcome.LoseAbility);
        staticText = "It's a Skeleton in addition to its other types and has no abilities";
    }

    private XuIfitOsteoharmonistEffect(final XuIfitOsteoharmonistEffect effect) {
        super(effect);
    }

    @Override
    public XuIfitOsteoharmonistEffect copy() {
        return new XuIfitOsteoharmonistEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addSubType(game, SubType.SKELETON);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
