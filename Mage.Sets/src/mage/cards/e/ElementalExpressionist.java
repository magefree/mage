package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileSourceReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Elemental44Token;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalExpressionist extends CardImpl {

    public ElementalExpressionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}{U/R}{U/R}{U/R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, choose target creature you control. Until end of turn, it gains "If this creature would leave the battlefield, exile it instead of putting it anywhere else" and "When you exile this creature, create a 4/4 blue and red Elemental creature token."
        Ability ability = new MagecraftAbility(new GainAbilityTargetEffect(
                new SimpleStaticAbility(new LeaveBattlefieldExileSourceReplacementEffect("this creature")),
                Duration.EndOfTurn, "choose target creature you control. Until end of turn, " +
                "it gains \"If this creature would leave the battlefield, exile it instead of putting it anywhere else\""
        ));
        ability.addEffect(new GainAbilityTargetEffect(
                new ElementalExpressionistTriggeredAbility(), Duration.EndOfTurn,
                "and \"When this creature is put into exile, create a 4/4 blue and red Elemental creature token.\""
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ElementalExpressionist(final ElementalExpressionist card) {
        super(card);
    }

    @Override
    public ElementalExpressionist copy() {
        return new ElementalExpressionist(this);
    }
}

class ElementalExpressionistTriggeredAbility extends ZoneChangeTriggeredAbility {

    ElementalExpressionistTriggeredAbility() {
        super(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.EXILED, new CreateTokenEffect(new Elemental44Token()), "", false);
    }

    private ElementalExpressionistTriggeredAbility(final ElementalExpressionistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElementalExpressionistTriggeredAbility copy() {
        return new ElementalExpressionistTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTarget() == null || zEvent.getTarget() != getSourcePermanentOrLKI(game)) {
            return false;
        }
        // custom check cause ZoneChangeTriggeredAbility for source object only
        return (fromZone == null || zEvent.getFromZone() == fromZone)
                && (zEvent.getToZone() == toZone || zEvent.getOriginalToZone() == toZone);
    }

    @Override
    public String getRule() {
        return "When this creature is put into exile, create a 4/4 blue and red Elemental creature token.";
    }
}
