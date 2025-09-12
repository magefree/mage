package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TocasiaDigSiteMentor extends CardImpl {

    public TocasiaDigSiteMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Creatures you control have vigilance and "{T}: Surveil 1."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new SimpleActivatedAbility(
                        new SurveilEffect(1, false), new TapSourceCost()
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and \"{T}: Surveil 1.\""));
        this.addAbility(ability);

        // {2}{G}{G}{W}{W}{U}{U}, Exile Tocasia, Dig Site Mentor from your graveyard: Return any number of target artifact cards with total mana value 10 or less from your graveyard to the battlefield. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new ManaCostsImpl<>("{2}{G}{G}{W}{W}{U}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TocasiaDigSiteMentorTarget());
        this.addAbility(ability);
    }

    private TocasiaDigSiteMentor(final TocasiaDigSiteMentor card) {
        super(card);
    }

    @Override
    public TocasiaDigSiteMentor copy() {
        return new TocasiaDigSiteMentor(this);
    }
}

class TocasiaDigSiteMentorTarget extends TargetCardInYourGraveyard {

    private static final FilterArtifactCard filterStatic = new FilterArtifactCard(
            "artifact cards with total mana value 10 or less from your graveyard"
    );

    TocasiaDigSiteMentorTarget() {
        super(0, Integer.MAX_VALUE, filterStatic, false);
    }

    private TocasiaDigSiteMentorTarget(final TocasiaDigSiteMentorTarget target) {
        super(target);
    }

    @Override
    public TocasiaDigSiteMentorTarget copy() {
        return new TocasiaDigSiteMentorTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 10, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 10, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
