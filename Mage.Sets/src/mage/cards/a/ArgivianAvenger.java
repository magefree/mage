package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgivianAvenger extends CardImpl {

    public ArgivianAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}: Until end of turn, Argivian Avenger gets -1/-1 and gains your choice of flying, vigilance, deathtouch, or haste.
        this.addAbility(new SimpleActivatedAbility(new ArgivianAvengerEffect(), new GenericManaCost(1)));
    }

    private ArgivianAvenger(final ArgivianAvenger card) {
        super(card);
    }

    @Override
    public ArgivianAvenger copy() {
        return new ArgivianAvenger(this);
    }
}

class ArgivianAvengerEffect extends OneShotEffect {

    private static final Map<String, Ability> abilityMap = new HashMap<>();

    static {
        abilityMap.put("Flying", FlyingAbility.getInstance());
        abilityMap.put("Vigilance", VigilanceAbility.getInstance());
        abilityMap.put("Deathtouch", DeathtouchAbility.getInstance());
        abilityMap.put("Haste", HasteAbility.getInstance());
    }

    ArgivianAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, {this} gets -1/-1 " +
                "and gains your choice of flying, vigilance, deathtouch, or haste";
    }

    private ArgivianAvengerEffect(final ArgivianAvengerEffect effect) {
        super(effect);
    }

    @Override
    public ArgivianAvengerEffect copy() {
        return new ArgivianAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        game.addEffect(new BoostSourceEffect(-1, -1, Duration.EndOfTurn), source);
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(abilityMap.keySet());
        player.choose(outcome, choice, game);
        Ability ability = abilityMap.getOrDefault(choice.getChoice(), null);
        if (ability != null) {
            game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}
