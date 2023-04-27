package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author LevelX2
 */
public final class MistveilPlains extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control two or more white permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public MistveilPlains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.PLAINS);

        // <i>({tap}: Add {W}.)</i>
        this.addAbility(new WhiteManaAbility());

        // Mistveil Plains enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {W}, {tap}: Put target card from your graveyard on the bottom of your library. Activate this ability only if you control two or more white permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new MistveilPlainsGraveyardToLibraryEffect(),
                new ManaCostsImpl<>("{W}"),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1)
        );
        ability.addTarget(new TargetCardInYourGraveyard());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private MistveilPlains(final MistveilPlains card) {
        super(card);
    }

    @Override
    public MistveilPlains copy() {
        return new MistveilPlains(this);
    }
}

class MistveilPlainsGraveyardToLibraryEffect extends OneShotEffect {

    public MistveilPlainsGraveyardToLibraryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target card from your graveyard on the bottom of your library";
    }

    public MistveilPlainsGraveyardToLibraryEffect(final MistveilPlainsGraveyardToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public MistveilPlainsGraveyardToLibraryEffect copy() {
        return new MistveilPlainsGraveyardToLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (card == null || player == null
                || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        return player.putCardsOnBottomOfLibrary(card, game, source, false);
    }
}
