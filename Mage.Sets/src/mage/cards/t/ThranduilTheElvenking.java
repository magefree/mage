package mage.cards.t;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class ThranduilTheElvenking extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF, "another legendary Elf you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ThranduilTheElvenking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Thranduil has all activated abilities of all Elf cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ThranduilTheElvenkingEffect()));

        // Whenever another legendary Elf you control enters, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new DrawDiscardControllerEffect(2, 1), filter
        ));
    }

    private ThranduilTheElvenking(final ThranduilTheElvenking card) {
        super(card);
    }

    @Override
    public ThranduilTheElvenking copy() {
        return new ThranduilTheElvenking(this);
    }
}

class ThranduilTheElvenkingEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard(SubType.ELF, "Elf cards");

    ThranduilTheElvenkingEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all Elf cards in your graveyard";
        this.dependendToTypes.add(DependencyType.AddingAbility); // Yixlid Jailer
    }

    private ThranduilTheElvenkingEffect(final ThranduilTheElvenkingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Set<Ability> abilities = player.getGraveyard()
            .getCards(filter, source.getControllerId(), source, game)
            .stream()
            .map(card -> card.getAbilities(game))
            .flatMap(Collection::stream)
            .filter(Ability::isActivatedAbility)
            .collect(Collectors.toSet());
        for (Ability ability : abilities) {
            permanent.addAbility(ability, source.getSourceId(), game, true);
        }
        return true;
    }

    @Override
    public ThranduilTheElvenkingEffect copy() {
        return new ThranduilTheElvenkingEffect(this);
    }
}
