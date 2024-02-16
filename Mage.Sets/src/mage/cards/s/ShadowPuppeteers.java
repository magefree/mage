package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FaerieRogueToken;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShadowPuppeteers extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ShadowPuppeteers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // When Shadow Puppeteers enters the battlefield, create two 1/1 black Faerie Rogue creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FaerieRogueToken(), 2)));

        // Whenever a creature you control with flying attacks, you may have it become a red Dragon with base power and toughness 4/4 in addition to its other colors and types until end of turn.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                Zone.BATTLEFIELD, new ShadowPuppeteersContinousEffect(),
                true, filter, true
        ));
    }

    private ShadowPuppeteers(final ShadowPuppeteers card) {
        super(card);
    }

    @Override
    public ShadowPuppeteers copy() {
        return new ShadowPuppeteers(this);
    }
}

class ShadowPuppeteersContinousEffect extends ContinuousEffectImpl {

    ShadowPuppeteersContinousEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "have it become a red Dragon with base power and toughness 4/4 "
                + "in addition to its other colors and types until end of turn";
    }

    private ShadowPuppeteersContinousEffect(final ShadowPuppeteersContinousEffect effect) {
        super(effect);
    }

    @Override
    public ShadowPuppeteersContinousEffect copy() {
        return new ShadowPuppeteersContinousEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addSubType(game, SubType.DRAGON);
                break;
            case ColorChangingEffects_5:
                permanent.getColor(game).setRed(true);
                break;
            case PTChangingEffects_7:
                permanent.getToughness().setModifiedBaseValue(4);
                permanent.getPower().setModifiedBaseValue(4);
        }
        return true;
    }
}
