package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class LorePositronicProvocateur extends CardImpl {

    public LorePositronicProvocateur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANDROID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Lore enters, choose one --
        // * Target creature gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 0));
        ability.addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        ability.addTarget(new TargetCreaturePermanent());

        // * Surveil 2.
        ability.addMode(new Mode(new SurveilEffect(2)));
        this.addAbility(ability);
    }

    private LorePositronicProvocateur(final LorePositronicProvocateur card) {
        super(card);
    }

    @Override
    public LorePositronicProvocateur copy() {
        return new LorePositronicProvocateur(this);
    }
}
