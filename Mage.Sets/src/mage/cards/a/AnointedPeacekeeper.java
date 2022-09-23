package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ChosenNamePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class AnointedPeacekeeper extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells with the chosen name");

    static {
        filter.add(ChosenNamePredicate.instance);
    }

    public AnointedPeacekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As Anointed Peacekeeper enters the battlefield, look at an opponent's hand, then choose any card name.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL, true)));

        // Spells your opponents cast with the chosen name cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostIncreasingAllEffect(2, filter, TargetController.OPPONENT)
                        .setText("spells your opponents cast with the chosen name cost {2} more to cast")
        ));

        // Activated abilities of sources with the chosen name cost {2} more to activate unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(new AnointedPeacekeeperEffect()));
    }

    private AnointedPeacekeeper(final AnointedPeacekeeper card) {
        super(card);
    }

    @Override
    public AnointedPeacekeeper copy() {
        return new AnointedPeacekeeper(this);
    }
}

class AnointedPeacekeeperEffect extends CostModificationEffectImpl {

    public AnointedPeacekeeperEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.staticText = "Activated abilities of sources with the chosen name cost {2} more to activate unless they're mana abilities.";
    }

    private AnointedPeacekeeperEffect(final AnointedPeacekeeperEffect effect) {
        super(effect);
    }

    @Override
    public AnointedPeacekeeperEffect copy() {
        return new AnointedPeacekeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() != AbilityType.ACTIVATED) {
            return false;
        }
        MageObject activatedSource = game.getObject(abilityToModify.getSourceId());
        if (activatedSource == null) {
            return false;
        }
        String chosenName = (String) game.getState().getValue(
                source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY
        );
        return CardUtil.haveSameNames(activatedSource, chosenName, game);
    }
}
