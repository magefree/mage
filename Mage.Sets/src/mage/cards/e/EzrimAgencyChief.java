package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EzrimAgencyChief extends CardImpl {

    public EzrimAgencyChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARCHON);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ezrim, Agency Chief enters the battlefield, investigate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(2)));

        // {1}, Sacrifice an artifact: Ezrim gains your choice of vigilance, lifelink, or hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(new EzrimAgencyChiefEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private EzrimAgencyChief(final EzrimAgencyChief card) {
        super(card);
    }

    @Override
    public EzrimAgencyChief copy() {
        return new EzrimAgencyChief(this);
    }
}

class EzrimAgencyChiefEffect extends OneShotEffect {

    private static final Map<String, Ability> abilityMap = new HashMap<>();

    static {
        abilityMap.put("Vigilance", VigilanceAbility.getInstance());
        abilityMap.put("Lifelink", LifelinkAbility.getInstance());
        abilityMap.put("Hexproof", HexproofAbility.getInstance());
    }

    EzrimAgencyChiefEffect() {
        super(Outcome.Benefit);
        staticText = "{this} gains your choice of vigilance, lifelink, or hexproof until end of turn";
    }

    private EzrimAgencyChiefEffect(final EzrimAgencyChiefEffect effect) {
        super(effect);
    }

    @Override
    public EzrimAgencyChiefEffect copy() {
        return new EzrimAgencyChiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(new HashSet<>(abilityMap.keySet()));
        player.choose(outcome, choice, game);
        Ability ability = abilityMap.getOrDefault(choice.getChoice(), null);
        if (ability != null) {
            game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}
