package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LazotepQuarry extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DESERT, "Desert");

    public LazotepQuarry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice a creature: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);

        // {X}{2}, {T}, Sacrifice a Desert: Exile target creature card with mana value X from your graveyard. Create a token that's a copy of it, except it's a 4/4 black Zombie. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new LazotepQuarryTargetEffect(),
                new ManaCostsImpl<>("{X}{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        this.addAbility(ability);
    }

    private LazotepQuarry(final LazotepQuarry card) {
        super(card);
    }

    @Override
    public LazotepQuarry copy() {
        return new LazotepQuarry(this);
    }
}

class LazotepQuarryTargetEffect extends OneShotEffect {

    LazotepQuarryTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card with mana value X from your graveyard. "
                + "Create a token that's a copy of it, except it's a 4/4 black Zombie";
    }

    private LazotepQuarryTargetEffect(final LazotepQuarryTargetEffect effect) {
        super(effect);
    }

    @Override
    public LazotepQuarryTargetEffect copy() {
        return new LazotepQuarryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (card == null || controller == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 1,
                false, false, null, 4, 4, false
        );
        effect.setTargetPointer(new FixedTarget(card, game));
        effect.setOnlySubType(SubType.ZOMBIE);
        effect.setOnlyColor(ObjectColor.BLACK);
        effect.apply(game, source);
        return true;
    }

}