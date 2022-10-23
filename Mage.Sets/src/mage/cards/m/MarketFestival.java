package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MarketFestival extends CardImpl {

    public MarketFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors (in addition to the mana the land produces).
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new MarketFestivalManaEffect()));
    }

    private MarketFestival(final MarketFestival card) {
        super(card);
    }

    @Override
    public MarketFestival copy() {
        return new MarketFestival(this);
    }
}

class MarketFestivalManaEffect extends ManaEffect {

    MarketFestivalManaEffect() {
        super();
        this.staticText = "its controller adds two mana in any combination of colors";
    }

    private MarketFestivalManaEffect(final MarketFestivalManaEffect effect) {
        super(effect);
    }

    @Override
    public MarketFestivalManaEffect copy() {
        return new MarketFestivalManaEffect(this);
    }

    public Player getPlayer(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return null;
        }
        return game.getPlayer(sourceObject.getControllerId());
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = getPlayer(game, source);
            return ManaChoice.chooseAnyColor(controller, game, 2);
        }
        return new Mana();
    }

}
