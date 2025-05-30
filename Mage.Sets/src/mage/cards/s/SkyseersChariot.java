package mage.cards.s;

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
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class SkyseersChariot extends CardImpl {

    public SkyseersChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As this Vehicle enters, choose a nonland card name.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));
        // Activated abilities of sources with the chosen name cost {2} more to activate.
        this.addAbility(new SimpleStaticAbility(new SkyseersChariotEffect()));

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private SkyseersChariot(final SkyseersChariot card) {
        super(card);
    }

    @Override
    public SkyseersChariot copy() {
        return new SkyseersChariot(this);
    }
}

class SkyseersChariotEffect extends CostModificationEffectImpl {

    SkyseersChariotEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities of sources with the chosen name cost {2} more to activate";
    }

    private SkyseersChariotEffect(final SkyseersChariotEffect effect) {
        super(effect);
    }

    @Override
    public SkyseersChariotEffect copy() {
        return new SkyseersChariotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        MageObject activatedSource = game.getObject(abilityToModify.getSourceId());
        if (activatedSource == null) {
            return false;
        }
        String chosenName = (String) game.getState().getValue(
                source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY
        );
        return CardUtil.haveSameNames(activatedSource, chosenName, game)
                && abilityToModify.isActivatedAbility();
    }
}
