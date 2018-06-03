
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author Plopman
 */
public final class SacellumGodspeaker extends CardImpl {

    public SacellumGodspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Reveal any number of creature cards with power 5 or greater from your hand. Add {G} for each card revealed this way.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SacellumGodspeakerEffect(), new TapSourceCost()));
    }

    public SacellumGodspeaker(final SacellumGodspeaker card) {
        super(card);
    }

    @Override
    public SacellumGodspeaker copy() {
        return new SacellumGodspeaker(this);
    }
}

class SacellumGodspeakerEffect extends ManaEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with power 5 or greater from your hand");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public SacellumGodspeakerEffect() {
        super();
        staticText = "Reveal any number of creature cards with power 5 or greater from your hand. Add {G} for each card revealed this way";
    }

    public SacellumGodspeakerEffect(final SacellumGodspeakerEffect effect) {
        super(effect);
    }

    @Override
    public SacellumGodspeakerEffect copy() {
        return new SacellumGodspeakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        if (target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game)) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            game.getPlayer(source.getControllerId()).getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return Mana.GreenMana(controller.getHand().count(filter, game));
            }
        }
        TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        if (target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game)) {
            return Mana.GreenMana(target.getTargets().size());
        }
        return null;
    }

}
