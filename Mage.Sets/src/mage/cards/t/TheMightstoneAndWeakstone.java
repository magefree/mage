package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMightstoneAndWeakstone extends CardImpl {

    public TheMightstoneAndWeakstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.POWERSTONE);

        this.meldsWithClazz = mage.cards.u.UrzaLordProtector.class;

        // When The Mightstone and Weakstone enters the battlefield, choose one --
        // * Draw two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2));

        // * Target creature gets -5/-5 until end of turn.
        ability.addMode(new Mode(new BoostTargetEffect(-5, -5)).addTarget(new TargetCreaturePermanent()));
        this.addAbility(ability);

        // {T}: Add {C}{C}. This mana can't be spent to cast nonartifact spells.
        this.addAbility(new ConditionalColorlessManaAbility(2, PowerstoneToken.makeBuilder()));

        // (Melds with Urza, Lord Protector)
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("<i>(Melds with Urza, Lord Protector)</i>")
        ));
    }

    private TheMightstoneAndWeakstone(final TheMightstoneAndWeakstone card) {
        super(card);
    }

    @Override
    public TheMightstoneAndWeakstone copy() {
        return new TheMightstoneAndWeakstone(this);
    }
}
