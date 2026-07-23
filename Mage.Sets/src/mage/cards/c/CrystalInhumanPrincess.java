package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class CrystalInhumanPrincess extends CardImpl {

    public CrystalInhumanPrincess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Whenever you cast a noncreature spell, Crystal deals X damage to each opponent, where X is the number of colors that spell is
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(CrystalInhumanPrincessSpellValue.instance, TargetController.OPPONENT)
                        .setText("{this} deals X damage to each opponent, where X is the number of colors that spell is"),
                StaticFilters.FILTER_SPELL_NON_CREATURE, false);
        this.addAbility(ability);

    }

    private CrystalInhumanPrincess(final CrystalInhumanPrincess card) {
        super(card);
    }

    @Override
    public CrystalInhumanPrincess copy() {
        return new CrystalInhumanPrincess(this);
    }
}

enum CrystalInhumanPrincessSpellValue implements DynamicValue {
    instance();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getColor(game).getColorCount() : 0;
    }

    @Override
    public CrystalInhumanPrincessSpellValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}