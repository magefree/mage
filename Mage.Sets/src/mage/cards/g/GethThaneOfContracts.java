package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

public class GethThaneOfContracts extends CardImpl {
    public GethThaneOfContracts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        //Other creatures you control get -1/-1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(-1, -1, Duration.WhileOnBattlefield, true)
        ));

        //{1}{B}{B}, {T}: Return target creature card from your graveyard to the battlefield. It gains “If this creature
        //would leave the battlefield, exile it instead of putting it anywhere else.” Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(false, false)
                        .setText("Return target creature card from your graveyard to the battlefield."),
                new ManaCostsImpl<>("{1}{B}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(
                new SimpleStaticAbility(new GethThaneOfContractsReplacementEffect()),
                Duration.Custom
        ).setText("It gains \"If this creature would leave the battlefield, exile it instead of putting it anywhere else.\""));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private GethThaneOfContracts(final GethThaneOfContracts card) {
        super(card);
    }

    @Override
    public GethThaneOfContracts copy() {
        return new GethThaneOfContracts(this);
    }
}

class GethThaneOfContractsReplacementEffect extends ReplacementEffectImpl {

    public GethThaneOfContractsReplacementEffect() {
        super(Duration.Custom, Outcome.Exile);
        this.staticText = "If {this} would leave the battlefield, exile it instead of putting it anywhere else.";
    }

    private GethThaneOfContractsReplacementEffect(final GethThaneOfContractsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GethThaneOfContractsReplacementEffect copy() {
        return new GethThaneOfContractsReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        UUID targetId = zEvent.getTargetId();
        return targetId != null && targetId.equals(source.getSourceId())
                && zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() != Zone.EXILED;
    }
}
