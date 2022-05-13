package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new SacellumGodspeakerEffect(), new TapSourceCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private SacellumGodspeaker(final SacellumGodspeaker card) {
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
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int count = controller.getHand().count(filter, game);
                if (count > 0) {
                    netMana.add(Mana.GreenMana(count));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            if (target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), source, game)) {
                return Mana.GreenMana(target.getTargets().size());
            }
        }
        return new Mana();
    }

}
